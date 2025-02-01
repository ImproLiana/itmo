
jtimetable = open('kamil_lab3/kn2.json', 'r', encoding="utf-8")
xtimetable = open('kamil_lab3/kn2.xml', 'w', encoding="utf-8")

lenj = 0
for k in jtimetable:
    lenj += 1
name = ''
# jtimetable[i]
for i in lenj:
    if i.count("\"") == 2:
        name = i.replace("\"", "<", 1).replace("\"", ">", 1).replace(":", "").replace("{", "").replace("   ", " ")
        xtimetable.write(name)
        name = name[name.index("<"):name.index(">")+1]
    else:
        while i.count("\"") == 4:
            c = i.replace("\"", "<", 1).replace("\"", ">", 1).replace("\"", "\'", 1)
            key = c[c.index("<"):c.index(">")+1]
            inf = c[c.index("\'")-1:c.index("\"")].replace("\'", "")
            xtimetable.write("       "+key+inf+key.replace("<", "</")+"\n")
    xtimetable.write("   "+name.replace("<", "</"))
