# variant:
print(466221%36)

jtimetable = open('lr3/timetable_json.json', 'r', encoding="utf-8")
ytimetable = open('lr3/timetable_yaml.yml', 'w', encoding="utf-8")

ytimetable.write('---\n')

for i in jtimetable:
    if '\"' in i:
        ytimetable.write(i.replace('\"', '').replace('{', '').replace(' :', ':').replace('   ',' ').replace(',', ''))

    
ytimetable.write('...')