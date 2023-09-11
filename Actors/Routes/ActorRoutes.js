const express = require('express');
const router = express.Router();
const { validateActor } = require('../utils/actorValidation'); // Importez les règles de validation

const ActorControllerReader = require('../Controllers/ActorControllerReader');
const ActorControllerWriter = require('../Controllers/ActorControllerWriter');

/**
 * @swagger
 * /actor/add-actor:
 *   post:
 *     summary: Crée un nouvel acteur
 *     description: Crée un nouvel acteur en utilisant les données fournies.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/Actor'  # Référence au schéma de données de l'acteur
 *     responses:
 *       200:
 *         description: Acteur créé avec succès.
 */
router.post('/actor/add-actor', validateActor, ActorControllerWriter.createActor);

/**
 * @swagger
 * /actor/update-actor:
 *   put:
 *     summary: Met à jour un acteur
 *     description: Met à jour les données d'un acteur existant en utilisant les données fournies.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/Actor'  # Référence au schéma de données de l'acteur
 *     responses:
 *       200:
 *         description: Acteur mis à jour avec succès.
 */
router.put('/actor/update-actor', ActorControllerWriter.updateActor);

/**
 * @swagger
 * /actor/delete-actor:
 *   delete:
 *     summary: Supprime un acteur
 *     description: Supprime un acteur existant en utilisant l'ID fourni.
 *     parameters:
 *       - in: query
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Acteur supprimé avec succès.
 */
router.delete('/actor/delete-actor', ActorControllerWriter.deleteActor);

/**
 * @swagger
 * /actor/show-actors:
 *   get:
 *     summary: Récupère la liste des acteurs
 *     description: Récupère la liste complète des acteurs dans la base de données.
 *     responses:
 *       200:
 *         description: Liste des acteurs récupérée avec succès.
 */
router.get('/actor/show-actors', ActorControllerReader.getAllActors);

/**
 * @swagger
 * /actor/search-actor:
 *   get:
 *     summary: Recherche des acteurs par nom ou prénom
 *     description: Recherche des acteurs par leur nom, prénom ou les deux.
 *     parameters:
 *       - in: query
 *         name: query
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Résultat de la recherche récupéré avec succès.
 */
router.get('/actor/search-actor', ActorControllerReader.searchByFirstNameOrLastNameOrBoth);

module.exports = router;
