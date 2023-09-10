// controllers/actorController.js

const Actor = require('./../Models/Actor');

class ActorController {
  static async getAllActors(req, res) {
    try {
      const actors = await Actor.find();
      res.json(actors);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Error retrieving actors' });
    }
  }

  static async createActor(req, res) {
    try {
      // Récupérer tous les champs du corps de la requête
      const actorData = req.body;
  
      // Créer une nouvelle instance d'Actor avec les champs fournis
      const actor = new Actor(actorData);
  
      // Enregistrer l'acteur dans la base de données
      await actor.save();
  
      // Répondre avec l'acteur créé
      res.json(actor);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Une erreur s\'est produite lors de la création de l\'acteur.' });
    }
  }
  

  static async updateActor(req, res) {
    const { name, age } = req.body;
    const { id } = req.params;
    try {
      const actor = await Actor.findByIdAndUpdate(id, { name, age }, { new: true });
      res.json(actor);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Error updating actor' });
    }
  }

  static async deleteActor(req, res) {
    const { id } = req.params;
    try {
      await Actor.findByIdAndRemove(id);
      res.json({ message: 'Actor deleted successfully' });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Error deleting actor' });
    }
  }
}

module.exports = ActorController;
