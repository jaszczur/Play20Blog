$(document).ready(function() {
    var placeChanged = function(newPlaceName, coords) {
        $('#physicalLocationName').val(newPlaceName);
        $('#physicalLocationLatitude').val(coords.latitude);
        $('#physicalLocationLongitude').val(coords.longitude);
    };

    var position = new Position(placeChanged);
});

