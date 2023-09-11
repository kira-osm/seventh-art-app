const Actor = require('../Models/Actor');
const logger = require('../Config/logger'); 

class ActorControllerReader {
  static async getAllActors(req, res) {
    try {
      const page = parseInt(req.query.page) || 1; 
      const size = parseInt(req.query.size) || 10; 
      const skip = (page - 1) * size;

      const actors = await Actor.find()
        .skip(skip)
        .limit(size);

      res.json(actors);

      // Journalisation en cas de succès
      logger.info('Liste des acteurs récupérée avec succès', { page, size, actorsCount: actors.length });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Error retrieving actors' });

      // Journalisation en cas d'erreur
      logger.error('Erreur lors de la récupération de la liste des acteurs', { error: error });
    }
  }

  static async searchByFirstNameOrLastNameOrBoth(req, res) {
    try {
      const { firstName, lastName } = req.query;
      let actors;

      if (lastName) {
        // Recherche par nom de famille
        actors = await Actor.find({
          lastName: { $regex: new RegExp(lastName, 'i') },
        });
      } else if (firstName) {
        // Recherche par prénom
        actors = await Actor.find({
          $or: [
            { firstName: { $regex: new RegExp(firstName, 'i') } },
            { lastName: { $regex: new RegExp(firstName, 'i') } },
          ],
        });
      } else {
        // Si ni le prénom ni le nom de famille ne sont fournis, renvoyer une liste vide
        actors = [];
      }

      if (actors.length === 0) {
        // Si aucun acteur n'est trouvé, renvoyer une réponse avec un code 404 (Not Found)
        res.status(404).json({ error: 'Aucun acteur trouvé' });
      } else {
        // Si des acteurs sont trouvés, renvoyer la liste
        res.json(actors);
      }

      // Journalisation en cas de succès
      logger.info('Résultat de la recherche récupéré avec succès', {
        firstName,
        lastName,
        actorsCount: actors.length,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Error searching for actors' });

      // Journalisation en cas d'erreur
      logger.error('Erreur lors de la recherche des acteurs', { error: error });
    }
  }
}

module.exports = ActorControllerReader;
