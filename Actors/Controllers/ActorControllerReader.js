const Actor = require('../Models/Actor');

class ActorControllerReader {
  static async getAllActors(req, res) {
    try {
      const actors = await Actor.find();
      res.json(actors);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Error retrieving actors' });
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
          
      } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error searching for actors' });
      }
    }
}
module.exports = ActorControllerReader;
