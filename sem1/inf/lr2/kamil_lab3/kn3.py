# def parse_json(json_string):   
#     """Простой парсер JSON, который возвращает список словарей."""
#     json_string = json_string.strip()
#     if json_string[0] != '[' or json_string[-1] != ']':
#          raise ValueError("Неверный формат JSON: должен начинаться с '[' и заканчиваться на ']'")
#     json_string = json_string[1:-1].strip()  # Убираем квадратные скобки
#     items = []
#     while json_string:        # Находим конец объекта
#         end_index = json_string.find('},')
#         if end_index == -1:
#             end_index = json_string.find('}') + 1
#         item_string = json_string[:end_index].strip()
#         json_string = json_string[end_index + 1:].strip()  # Убираем текущий объект из строки
#         # Парсим объект
#         item = {}
#         item_string = item_string[1:-1].strip()  # Убираем фигурные скобки
#         pairs = item_string.split(',')
#         for pair in pairs:
#             key, value = pair.split(':', 1)
#             key = key.strip().strip('"')
#             value = value.strip().strip('"')
#             item[key] = value
#         items.append(item)
#     return items

# def dict_to_xml(data):
#     """Конвертирует список словарей в XML строку."""
#     xml_lines = ['<timetable>']
#     for entry in data:
#         xml_lines.append('  <entry>')
#         for key, value in entry.items():
#             xml_lines.append(f'    <{key}>{value}</{key}>')
#             xml_lines.append('  </entry>')
#     xml_lines.append('</timetable>')
#     return '\n'.join(xml_lines)
# def save_to_file(filename, content):
#     """Сохраняет строку в файл."""
#     with open(filename, 'w', encoding='utf-8') as file:
#         file.write(content)
# def main():
#     # Чтение JSON файла
#         with open('C:/Users/User/PycharmProjects/pythonProject6/.venv/timetable/timetable_json.json', 'r', encoding='utf-8') as file:
#         json_data = file.read()
#     # Парсим JSON данные
#         parsed_data = parse_json(json_data)
#     # Конвертируем в XML
#     xml_content = dict_to_xml(parsed_data)
#     # Сохраняем в файл    save_to_file('C:/Users/User/PycharmProjects/pythonProject6/.venv/timetable/timetable_xml.xml', xml_content)
#     print("Конвертация завершена! Файл 'timetable_xml.xml' создан.")
# if __name__ == "__main__":
#     main()