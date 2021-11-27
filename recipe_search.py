import pymysql.cursors
import json
import os

count = 0
re_id = []
re_names = []

#mysql DB 연동
conn = pymysql.connect( 
host = "foodrecommender9.c6v4d5m6amga.ap-northeast-2.rds.amazonaws.com", #ex) '127.0.0.1' 
port = 3306, 
user = "recipe", #ex) root 
password = "FoodRecommender9", 
database = "sys" 
)

curs = conn.cursor()

def recipe_search():
    global count

    path = ".\Recipes_file"
    file_list = os.listdir(path)

    ingre_list = ["양파", "당근", "버섯"]

    sql1 = "select recipe_ingre from RecipeData;"
    curs.execute(sql1)
    recipe_ingre = list(curs.fetchall())
    conn.commit()

    for r_ingre in recipe_ingre:
        r_ingre0 = r_ingre[0]
        contain = 0
        count = count + 1
        r_ingre1 = r_ingre0[1:-1]
        r_ingre2 = r_ingre1.replace("'","")
        r_ingre3 = r_ingre2.replace(" ","")
        r_ingre4 = r_ingre3.split(',')
        for ingre in ingre_list:
            if ingre in r_ingre4:
                contain = contain + 1

        if contain == len(ingre_list):
            sql2 = "select recipe_name from RecipeData where id = %s;"
            curs.execute(sql2, count)
            recipe_n = curs.fetchall()
            conn.commit()
            recipe_name = recipe_n[0]
            re_names.append(recipe_name[0])
            re_id.append(count)

    rec_id_str = food_check()

    while True:
        if rec_id_str == 0:
            rec_id_str = food_check()
        else:
            break

    with open(path + '\\' + rec_id_str + '.json', 'r', encoding='utf-8') as json_file:
        json_data = json.load(json_file)
        recipe_order = json_data["recipe"]
        page = 0
        print(recipe_order[page])
        while True:
            if page == (len(recipe_order) - 1):
                order = input("이전 또는 완성: ")
            else:
                order = input("이전 또는 다음: ")

            if order == '이전':
                page = page - 1
                if page < 0:
                    page = 0
                print(recipe_order[page])
            elif order == '다음':
                page = page + 1
                if page == len(recipe_order):
                    page = page - 1
                print(recipe_order[page])
            elif order == '완성':
                print("완성!!")
                break

def food_check():
    for a in re_names:
        print(a)

    name = input("음식: ")
    print(name)

    sql3 = "select id from RecipeData where recipe_name = %s;"
    curs.execute(sql3,name)
    db_id = list(curs.fetchall())
    conn.commit()

    for r_db_id in db_id:
        r_db_id_int = int(r_db_id[0])
        if r_db_id_int in re_id:
            re_id_int = r_db_id_int

    sql4 = "select recipe_id from RecipeData where id = %s;"
    curs.execute(sql4, re_id_int)
    r_id = list(curs.fetchall())
    conn.commit()

    sql5 = "select recipe_ingre from RecipeData where id = %s;"
    curs.execute(sql5, re_id_int)
    re_ingre = list(curs.fetchall())
    conn.commit()
    rec_ingre = re_ingre[0]
    print(rec_ingre[0])

    rr_id = r_id[0]
    rr_id_int = int(rr_id[0])
    rr_id_str = str(rr_id_int)

    while True:
        ingre_check = input("요리 시작 또는 재선택: ")
        if ingre_check == '시작':
            return rr_id_str
        elif ingre_check == '재선택':
            return 0

recipe_search()