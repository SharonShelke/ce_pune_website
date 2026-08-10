import re

def get_state_city(name, location):
    name = name.upper()
    loc = location.upper()
    if 'PIMPRI' in name or 'PIMPRI' in loc or 'DAPODI' in loc or 'SANGVI' in loc or 'BHOSARI' in loc or 'ALANDI' in loc or 'PIMPLE GURAV' in loc:
        return 'MH', 'Pimpri-Chinchwad'
    if 'BANGALORE' in name or 'BANGALORE' in loc:
        return 'KA', 'Bengaluru'
    if 'DEHRADUN' in name or 'DEHRADUN' in loc or 'UTTARAKHAND' in loc or 'MIMSHACH-4' in name or 'MIMSHACH-5' in name or 'MIMSHACH-6' in name or 'MIMSHACH-7' in name or 'PERFECTION' in name or 'PHOSTER' in name or 'VICTORIOUS' in name:
        if 'ONLINE' not in loc and not name == 'PERFECTION (P)':
            return 'UK', 'Dehradun'
    if 'HYDERABAD' in name or 'HYDERABAD' in loc or 'KUKATPALLY' in loc or 'HAFEEZPET' in loc or 'KONDAPUR' in loc or 'SIDDIPET' in loc or 'TELANGANA' in loc:
        return 'TG', 'Hyderabad'
    if 'BADVEL' in name or 'BADVEL' in loc or '(AP)' in name or 'GUDAM' in loc or 'SUNDARIAH' in loc or 'POOSALAVADA' in loc or 'RISHAW' in loc or 'FATHIMA' in loc or 'CHENDUVAIAH' in loc or 'SIVA NAGAR' in loc or 'ALM' in name or 'BHINDHALA' in loc:
        return 'AP', 'Badvel'
    if 'KARUNGAL' in loc or 'ZION NAGAR' in loc or 'MIDALAM' in loc or 'THISAYANVILAI' in loc or 'HENOTES' in name or 'MERCY' in name or 'RAPHA' in name:
        return 'TN', 'Kanyakumari'
    if 'NOIDA' in name or 'NOIDA' in loc or 'GR, UP' in loc or 'UPCOUNTRY' in loc or 'PHRONESIS' in name or 'GLORIOUS' in name or 'ABUNDANCE' in name or 'GLORY CELL' in name:
        return 'UP', 'Greater Noida'
    if 'VADODARA' in loc:
        return 'GJ', 'Vadodara'
    if 'FARIDABAD' in loc or 'DELHI' in loc:
        return 'HR', 'Faridabad'
    if 'GOA' in loc:
        return 'GA', 'Panaji'
    if 'NAGAR' in loc and 'VIDYARTHI' not in loc and 'SHIVAJI' not in loc and 'VIMAN' not in loc and 'KESHAV' not in loc and 'CHANDAN' not in loc and 'SIVA' not in loc and 'FATHIMA' not in loc and 'GOWRISHANKAR' not in loc and 'SUNDARIAH' not in loc and 'ZION' not in loc and 'PREM' not in loc and 'PRAGATHI' not in loc and 'PRASHANT' not in loc and 'WAKAD' not in loc and 'MALEGAON' not in loc and ('WABORI' in loc or 'NAGAR CITY' in loc or 'SANGAMNER' in loc):
        return 'MH', 'Ahilyanagar'
    if 'BEED' in loc or 'TORANWADE' in loc or 'JAMKHED' in loc:
        return 'MH', 'Beed'
    if 'SHRIRAMPUR' in loc:
        return 'MH', 'Ahilyanagar'
    if 'JAYSINGPUR' in loc or 'SHENOLI' in loc or 'KARAD' in loc:
        return 'MH', 'Kolhapur'
        
    return 'MH', 'Pune' # Default

with open('c:/Users/Sharon/IdeaProjects/ce_pune_website/src/main/java/com/christembassy/pune/DataInitializer.java', 'r') as f:
    content = f.read()

new_lines = []
for line in content.split('\n'):
    match = re.search(r'(new Fellowship\(".*?",\s*".*?",\s*".*?",\s*")(.*?)("\s*,\s*".*?",\s*".*?"\))', line)
    if match:
        name_match = re.search(r'new Fellowship\("(.*?)"', line)
        name = name_match.group(1) if name_match else ''
        loc = match.group(2)
        state, city = get_state_city(name, loc)
        
        new_line = line[:match.start()] + match.group(1) + loc + '", "' + state + '", "' + city + match.group(3) + line[match.end():]
        new_lines.append(new_line)
    else:
        new_lines.append(line)

with open('c:/Users/Sharon/IdeaProjects/ce_pune_website/src/main/java/com/christembassy/pune/DataInitializer.java', 'w') as f:
    f.write('\n'.join(new_lines))
