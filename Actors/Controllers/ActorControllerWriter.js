const Actor = require('./../Models/Actor');

class ActorControllerWriter {
  static async createActor(req, res) {
    try {
      const actorData = req.body;
      const actor = new Actor(actorData);
      await actor.save();
      res.json(actor);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Une erreur s\'est produite lors de la création de l\'acteur.' });
    }
  }

  static async updateActor(req, res) {
    try {
       // req.query pour extraire les paramètres de l'URL
      const { firstName, lastName } = req.query;
      
      //req.body pour extraire les données de mise à jour
      const updatedData = req.body; 
      
      const query = {
        $or: [
          { firstName },
          { lastName },
        ],
      };
  
      const updatedActor = await Actor.findOneAndUpdate(query, updatedData, { new: true });
  
      if (!updatedActor) {
        return res.status(404).json({ error: 'Actor not found' });
      }
  
      res.json({ message: 'Actor updated successfully', actor: updatedActor });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Error updating actor' });
    }
}

  static async deleteActor(req, res) {
    try {
      const { firstName, lastName } = req.query;

      if (!firstName && !lastName) {
        return res.status(400).json({ error: 'Veuillez fournir un prénom ou un nom de famille pour la suppression.' });
      }

      let deletedActor;

      if (firstName) {
        // Supprimer par prénom
        deletedActor = await Actor.findOneAndDelete({ firstName });
      } else {
        // Supprimer par nom de famille
        deletedActor = await Actor.findOneAndDelete({ lastName });
      }

      if (!deletedActor) {
        return res.status(404).json({ error: 'Aucun acteur trouvé pour la suppression.' });
      }

      res.json({ message: 'Acteur supprimé avec succès' });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Erreur lors de la suppression de l\'acteur' });
    }
  }
}

module.exports = ActorControllerWriter;
