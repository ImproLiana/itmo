import re
jtimetable = open('lr3/timetable_json.json', 'r', encoding="utf-8")
ytimetable = open('lr3/timetable_yaml.yml', 'w', encoding="utf-8")

ytimetable.write('---\n')

for i in jtimetable:
    if '\"' in i:
        r1 = re.findall(r'\s*', i)[0]
        r2 = re.findall(r'"(.+)" ?(: )', i)
        r = re.findall(r'"(.+)" ?(: )"(.[^\"]*)"', i)
        if len(r) != 0:
            ytimetable.write(' '*(len(r1)//3-1) + ''.join(r[0]) + '\n')
        elif len(r2) != 0:
            ytimetable.write(' '*(len(r1)//3-1) + ''.join(r2[0]) + '\n')
    
ytimetable.write('...')