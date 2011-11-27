
document.observe("dom:loaded", function() {
    var blog = new BlogAPI();
    
    blog.getEntries(function(entries) {
            console.log("dup11a");
        entries.each(function(entry) {
            console.log("dupa");
        
            var art = new Element('article');
            var pubdate = new Element('time', {pubdate: 'pubdate'}).update(entry.creationDate);
            var header = new Element('h3').update(entry.title);
            var section = new Element('section').update(entry.content);
            
            art.appendChild(header);
            art.appendChild(pubdate);
            art.appendChild(section);
            $('articles').appendChild(art);
        });
    });
});

