
var BlogAPI = function() {
    this.baseUrl = "http://localhost:9000";
};

BlogAPI.prototype = {

    getEntries: function(entriesListener) {
        console.log("Server call");
        new Ajax.Request(this.url("/entry/list"), {
            method: 'get',
            onSuccess: function(response) {
                console.log("Server call - OK");
                var ej = eval(response.responseText);
                entriesListener(ej);
            },
            onFailure: function() {
                console.log('lipa');
            }
        });
    },
    
    getEntry: function(id, entryListener) {
        new Ajax.Request(this.url("/entry/" + id), {
            method: 'get',
            onSuccess: function(response) {
                var blogEntryJson = response.responseText.evalJSON();
                entryListener(blogEntryJson);
            }
        });
    },
    
    url: function(page) {
        return this.baseUrl + page + "?format=json";
    }
};

