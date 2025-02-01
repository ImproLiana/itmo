import json
import yaml

jtimetable = open('lr3/timetable_json.json', 'r', encoding="utf-8")
ytimetable = open('lr3/timetable_yaml.yml', 'w', encoding="utf-8")

data = json.load(jtimetable)
yaml.dump(data, ytimetable, allow_unicode=True)

