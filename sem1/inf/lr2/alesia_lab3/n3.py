for x in range(100):
    schedule = open('schedule.xml', 'r', encoding="utf-8")
    timetable = open('timetable.json', 'w', encoding="utf-8")

    tab_counter = 1
    timetable.write('{\n')
    x = schedule.readlines()
    schedule.close()

    for i in range(len(x)):
        tag = x[i].strip().replace('<', '>').split('>')[1:-1]
        if i == len(x) - 1 or x[i + 1].strip()[:2] == '</':
            q = ''
        else:
            q = ','
        if len(tag) > 2:
            tag.pop()
        if len(tag) == 1:
            if tag[0][0] == '/':  # закрывающий
                timetable.write('\t' * tab_counter + '}' + q + '\n')  # закрытие }
                tab_counter -= 1  # табуляция влево
            else:
                timetable.write('\t' * tab_counter + '"' + tag[0] + '": {\n')  # открывающий
                tab_counter += 1  # табуляция вправо
        else:
            timetable.write('\t' * tab_counter + '"' + tag[0] + '" : "' + tag[1] + '" ' + q + '\n')

    timetable.write('}\n')
    timetable.close()
print("Хозяин, извольте проверить файл с расширением json")