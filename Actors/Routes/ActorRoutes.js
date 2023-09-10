const express = require('express');
const router = express.Router();
const { validateActor } = require('../utils/actorValidation'); // Importez les règles de validation

const ActorControllerReader = require('../Controllers/ActorControllerReader');
const ActorControllerWriter = require('../Controllers/ActorControllerWriter');




router.post('/actor/add-actor', validateActor, ActorControllerWriter.createActor);
router.put('/actor/update-actor', ActorControllerWriter.updateActor);
router.delete('/actor/delete-actor', ActorControllerWriter.deleteActor);


router.get('/actor/show-actors', ActorControllerReader.getAllActors);
router.get('/actor/search-actor', ActorControllerReader.searchByFirstNameOrLastNameOrBoth);

module.exports = router;
