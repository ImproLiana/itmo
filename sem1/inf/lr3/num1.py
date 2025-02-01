import re
variant = str(466221%5) + str(466221%4) + str(466221%7)
print(variant)
# ;<(
def count_smile(t):
    ans = len(re.findall(';<\(', t))
    return ans


text = '''
YOU KNEW THE DIFFERENCE
BETWEEN RIGHT AND WRONG
BUT YOU DID IT ANYWAY
'''
# 0
print('пример одни:', count_smile(text))
text1 = '''
открой окно, душно в комнате;<(
'''
# 1
print('пример два:', count_smile(text1))
text2 = '''
;<(()4632&723891::;2;31>;<(37191:7&$#%(#");<(&^3
'''
# 3
print('пример три:', count_smile(text2))
text3 = '''
emotions like happiness:), sadness:(, anger :<, can be combined like :)(, ;<(, 
so different versions have different meaning like :)( - dont get their own feelimgs, 
;<( can be disappointment
'''
# 2
print('пример четыре:', count_smile(text3))
text4 = '''
1 ;<( 
2 ;<( 
3 ;<( 
4 ;<( 
5 ;<(
6 ;<(
7 ;<(
8 ;<)
'''
# 7
print('пример пять:', count_smile(text4))