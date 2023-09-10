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

module.exports = ActorControllerWriter;
