$(document).ready(function() {
    var placeChanged = function(newPlace) {
        $('#physicalLocation').val(newPlace);
    };

    var position = new Position(placeChanged);
});

