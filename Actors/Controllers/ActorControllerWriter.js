const Actor = require("./../Models/Actor");
const { validationResult } = require("express-validator");
const logger = require("../Config/logger");
const sendEmail = require("../Services/emailService");

class ActorControllerWriter {

  

  static async createActor(req, res) {
    try {
      // Valider les données
      const errors = validationResult(req);
      if (!errors.isEmpty()) {
        return res.status(400).json({ errors: errors.array() });
      }
  
      const actorData = req.body;
      const actor = new Actor(actorData);
      await actor.save();
  
      const emailTo = "xxxx@gmail.com";
      const emailSubject = "Nouvel acteur ajouté";
      const emailHtmlContent = `
      <html>
        <body>
          <h1>Nouvel acteur ajouté</h1>
          <p>Prénom : ${actor.firstName}</p>
          <p>Nom de famille : ${actor.lastName}</p>
          <p>Âge : ${actor.age}</p>
          <p>Genre : ${actor.gender}</p>
          <p>Biographie : ${actor.biography}</p>
          <p>Date de naissance : ${actor.birthday}</p>
          <p>Lieu de naissance : ${actor.placeOfBirth}</p>
          <img src="cid:actorImage" alt="Nouvel acteur" />
        </body>
      </html>
    `;
  
      const imageBase64 = actor.image; // Récupérez l'image en base64 du modèle
  
      sendEmail(emailTo, emailSubject, emailHtmlContent, imageBase64);
  
      res.json(actor);
  
      // Journalisation en cas de succès
      logger.info("Acteur créé avec succès", { actor: actor });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        error: "Une erreur s'est produite lors de la création de l'acteur.",
      });
  
      // Journalisation en cas d'erreur
      logger.error("Erreur lors de la création de l'acteur", { error: error });
    }
  }


  
  static async updateActor(req, res) {
    try {
      // req.query pour extraire les paramètres de l'URL
      const { firstName, lastName } = req.query;

      // req.body pour extraire les données de mise à jour
      const updatedData = req.body;

      const query = {
        $or: [{ firstName }, { lastName }],
      };

      const updatedActor = await Actor.findOneAndUpdate(query, updatedData, {
        new: true,
      });

      if (!updatedActor) {
        return res.status(404).json({ error: "Actor not found" });
      }

      res.json({ message: "Actor updated successfully", actor: updatedActor });

      // Journalisation en cas de succès
      logger.info("Acteur mis à jour avec succès", { actor: updatedActor });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Error updating actor" });

      // Journalisation en cas d'erreur
      logger.error("Erreur lors de la mise à jour de l'acteur", {
        error: error,
      });
    }
  }

  static async deleteActor(req, res) {
    try {
      const { firstName, lastName } = req.query;

      if (!firstName && !lastName) {
        return res.status(400).json({
          error:
            "Veuillez fournir un prénom ou un nom de famille pour la suppression.",
        });
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
        return res
          .status(404)
          .json({ error: "Aucun acteur trouvé pour la suppression." });
      }

      res.json({ message: "Acteur supprimé avec succès" });

      // Journalisation en cas de succès
      logger.info("Acteur supprimé avec succès", { actor: deletedActor });
    } catch (error) {
      console.error(error);
      res
        .status(500)
        .json({ error: "Erreur lors de la suppression de l'acteur" });

      // Journalisation en cas d'erreur
      logger.error("Erreur lors de la suppression de l'acteur", {
        error: error,
      });
    }
  }
}

module.exports = ActorControllerWriter;
