const fs = require('fs');

let d_path = 'src/main/java/com/christembassy/pune/DataInitializer.java';
let d_content = fs.readFileSync(d_path, 'utf8');
let lines = d_content.split('\n');

for (let i = 0; i < lines.length; i++) {
    let line = lines[i];
    if (line.includes('new Fellowship(')) {
        // We need to parse arguments. Since they are strings, we can just split by ',' but beware of commas inside quotes.
        // Instead of a complex CSV parser, since we just want to remove the 6th argument (city), we can do this:
        
        let startIdx = line.indexOf('new Fellowship(') + 'new Fellowship('.length;
        let argsPart = line.substring(startIdx, line.lastIndexOf(')'));
        
        // simple parser
        let args = [];
        let currentArg = '';
        let inQuotes = false;
        
        for (let c = 0; c < argsPart.length; c++) {
            let char = argsPart[c];
            if (char === '"') {
                inQuotes = !inQuotes;
                currentArg += char;
            } else if (char === ',' && !inQuotes) {
                args.push(currentArg.trim());
                currentArg = '';
            } else {
                currentArg += char;
            }
        }
        args.push(currentArg.trim());
        
        if (args.length === 8) {
            args.splice(5, 1); // remove city (6th arg)
            let newArgsPart = args.join(', ');
            lines[i] = line.substring(0, startIdx) + newArgsPart + line.substring(line.lastIndexOf(')'));
        }
    }
}

fs.writeFileSync(d_path, lines.join('\n'));
console.log('Fixed DataInitializer');
