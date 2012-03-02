
document.observe("dom:loaded", function() {
    var blog = new BlogAPI();
    
    blog.getEntries(function(entries) {
            console.log("dup11a");
        entries.each(function(entry) {
            console.log("dupa");
        
            var art = new Element('article');
            var header = new Element('header');
            var title = new Element('h3').update(entry.title);
            var pubdate = new Element('time', {pubdate: 'pubdate'}).update(entry.creationDate);
            var location = new Element('p', {"class": "location"}).update(entry.location);
            var section = new Element('section').update(entry.content);
            
            header.appendChild(title);
            header.appendChild(pubdate);
            header.appendChild(location);
            art.appendChild(header);
            art.appendChild(section);
            $('articles').appendChild(art);
        });
    });
});

