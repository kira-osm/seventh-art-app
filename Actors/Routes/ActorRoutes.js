const express = require('express');
const router = express.Router();
const ActorController = require('../Controllers/actorController');

router.get('/actors', ActorController.getAllActors);
router.post('/actors', ActorController.createActor);
router.put('/actors/:id', ActorController.updateActor);
router.delete('/actors/:id', ActorController.deleteActor);

module.exports = router;
