const fs = require('fs');

// Fellowship.java
let f_path = 'src/main/java/com/christembassy/pune/Fellowship.java';
let content = fs.readFileSync(f_path, 'utf8');

content = content.replace(/\s*private String city;/g, '');
content = content.replace('String state, String city, String meetingTime', 'String state, String meetingTime');
content = content.replace(/\s*this\.city = city;/g, '');
content = content.replace(/\s*public String getCity\(\) \{ return city; \}/g, '');
content = content.replace(/\s*public void setCity\(String city\) \{ this\.city = city; \}/g, '');

fs.writeFileSync(f_path, content);


// DataInitializer.java
let d_path = 'src/main/java/com/christembassy/pune/DataInitializer.java';
let d_content = fs.readFileSync(d_path, 'utf8');

d_content = d_content.replace(/(new Fellowship\([^,]+,\s*[^,]+,\s*[^,]+,\s*[^,]+,\s*[^,]+),\s*[^,]+,\s*([^,]+,\s*[^,]+\))/g, '$1, $2');

d_content = d_content.replace(/\s*e\.setCity\(h\.getCity\(\)\);/g, '');

fs.writeFileSync(d_path, d_content);
console.log('Done!');
