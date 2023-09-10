const express = require('express');
const router = express.Router();
const ActorControllerReader = require('../Controllers/ActorControllerReader');
const ActorControllerWriter = require('../Controllers/ActorControllerWriter');

router.get('/show-actors', ActorControllerReader.getAllActors);
router.get('/search-actor', ActorControllerReader.searchByFirstNameOrLastNameOrBoth);



router.post('/add-actor', ActorControllerWriter.createActor);
router.put('/actors/:id', ActorControllerWriter.updateActor);
router.delete('/actors/:id', ActorControllerWriter.deleteActor);

module.exports = router;
