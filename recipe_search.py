# -*- coding: utf-8 -*-
import pymysql.cursors
import json
import os
from yolo import labels_kor

conn = pymysql.connect( 
host = "foodrecommender9.c6v4d5m6amga.ap-northeast-2.rds.amazonaws.com", #ex) '127.0.0.1' 
port = 3306, 
user = "recipe", #ex) root 
password = "FoodRecommender9", 
database = "sys" 
)

curs = conn.cursor()

path = "./Recipes_file"
file_list = os.listdir(path)


id_num = []
re_names = []

#식재료 기반 서치
def ingredient_search():
    recipes_info1 = []    #식재료 기반으로 찾은 레시피 목록
    necessary_ingre = []  #추가로 필요한 재료 목록

    ingre_list = labels_kor  #선택한 식재료 리스트

    sql1 = "select recipe_ingre,id from RecipeData order by recipe_name;"
    curs.execute(sql1)
    recipe_ingre_id = list(curs.fetchall())
    conn.commit()

    for r_ingre_id in recipe_ingre_id:
        recipe_info1 = []       #식재료 기반으로 찾은 레시피
        r_ingre0 = r_ingre_id[0]
        r_id0 = r_ingre_id[1]
        saved_ingre = []     #가지고 있는 식재료
        contain = 0
        #DB에서 식재료 데이터 불러온 후 전처리
        r_ingre1 = r_ingre0[1:-1]
        r_ingre2 = r_ingre1.replace("'","")
        r_ingre3 = r_ingre2.replace(" ","")
        r_ingre4 = r_ingre3.split(',')
        for ingre in ingre_list:
            if ingre in r_ingre4:
                saved_ingre.append(ingre)
                contain = contain + 1

        if contain == len(ingre_list):
            sql2 = "select recipe_name from RecipeData where id = %s;"
            curs.execute(sql2, r_id0)
            recipe_n = curs.fetchall()
            conn.commit()

            sql3 = "select recipe_id from RecipeData where id = %s;"
            curs.execute(sql3, r_id0)
            recipe_idd = curs.fetchall()
            conn.commit()
            rrr_id = recipe_idd[0]
            rrr_id_int = int(rrr_id[0])
            rrr_id_str = str(rrr_id_int)
            with open(path + '/' + rrr_id_str + '.json', 'r', encoding='utf-8') as json_file:
                json_data = json.load(json_file)
                recipe_order_count1 = json_data["recipe"]

            recipe_name = recipe_n[0]
            re_names.append(recipe_name[0])
            id_num.append(r_id0)
            setr_ingre4 = set(r_ingre4)
            setsaved_ingre = set(saved_ingre)
            necessary_ingre = setr_ingre4 - setsaved_ingre
            recipe_info1.append(len(necessary_ingre))
            recipe_info1.append(recipe_name[0])
            recipe_info1.append(len(recipe_order_count1))
            recipe_info1.append(saved_ingre)
            recipe_info1.append(necessary_ingre)
            recipes_info1.append(recipe_info1)


    for recipe_infor1 in recipes_info1: #기본 요리이름 ㄱㄴㄷ 순
       print(recipe_infor1[0])   #추가로 필요한 재료 수
       print(recipe_infor1[1])   #요리 이름
       print(recipe_infor1[2])   #레시피 단계
       print(recipe_infor1[3])   #나에게 저장된 재료
       print(recipe_infor1[4])   #추가로 필요한 재료
       print(" ")

    # recipes_info1.sort()  
    # for recipe_infor1 in recipes_info1:   #추가로 필요한 재료가 적은 순
    #     print(recipe_infor1[0])   #추가로 필요한 재료 수
    #     print(recipe_infor1[1])   #요리 이름
    #     print(recipe_infor1[2])   #레시피 단계
    #     print(recipe_infor1[3])   #나에게 저장된 재료
    #     print(recipe_infor1[4])   #추가로 필요한 재료
    #     print(" ")

    name = input("만들 음식: ")
    print(name)

    return name

#이름기반 서치
def name_search(wanted):
    recipes_info2 = []
    sql4 = "select recipe_name,id from RecipeData order by recipe_name;"
    curs.execute(sql4)
    recipes_name_id = list(curs.fetchall())
    conn.commit()

    for recipe_name_id in recipes_name_id:
        recipe_info2 = []
        recipe_name0 = recipe_name_id[0]
        recipe_id0 = recipe_name_id[1]
        if wanted in recipe_name0:
            sql5 = "select recipe_ingre from RecipeData where recipe_name = %s;"
            curs.execute(sql5, recipe_name0)
            recipe_ing = list(curs.fetchall())
            conn.commit()
            for r_ingre in recipe_ing:
                r_ingre0 = r_ingre[0]
                r_ingre1 = r_ingre0[1:-1]
                r_ingre2 = r_ingre1.replace("'","")
                r_ingre3 = r_ingre2.replace(" ","")
                r_ingre4 = r_ingre3.split(',')

            sql6 = "select recipe_id from RecipeData where id = %s;"
            curs.execute(sql6, recipe_id0)
            recipe_iddd = curs.fetchall()
            conn.commit()
            rrrr_id = recipe_iddd[0]
            rrrr_id_int = int(rrrr_id[0])
            rrrr_id_str = str(rrrr_id_int)
            with open(path + '\\' + rrrr_id_str + '.json', 'r', encoding='utf-8') as json_file:
                json_data = json.load(json_file)
                recipe_order_count2 = json_data["recipe"]
            recipe_info2.append(recipe_name0)
            recipe_info2.append(len(recipe_order_count2))
            recipe_info2.append(r_ingre4)
            recipes_info2.append(recipe_info2)

            re_names.append(recipe_name0)
            id_num.append(recipe_id0)

    for recipe_infor2 in recipes_info2:    #기본 요리 이름 ㄱㄴㄷ 순서
        print(recipe_infor2[0])   #레시피 이름
        print(recipe_infor2[1])   #레시피 단계
        print(recipe_infor2[2])   #레시피 재료
        print("  ")
    #for a in re_names:
    #    print(a)

    name = input("만들 음식: ")
    print(name)

    return name


#음식 선택
def select_food(name):
    sql7 = "select id from RecipeData where recipe_name = %s;"
    curs.execute(sql7,name)
    db_id = list(curs.fetchall())
    conn.commit()

    for r_db_id in db_id:
        r_db_id_int = int(r_db_id[0])
        if r_db_id_int in id_num:
            id_num_int = r_db_id_int

    sql8 = "select recipe_id from RecipeData where id = %s;"
    curs.execute(sql8, id_num_int)
    r_id = list(curs.fetchall())
    conn.commit()

    rr_id = r_id[0]
    rr_id_int = int(rr_id[0])
    rr_id_str = str(rr_id_int)

    while True:
        ingre_check = input("요리 시작 또는 재선택: ")
        if ingre_check == '시작':
            return rr_id_str
        elif ingre_check == '재선택':
            return 0

#레시피 제공
def recommend_recipe():

    way = input("1.레시피기반 2.이름기반: ")
    
    if way == "1":
        re_name = ingredient_search()
        rec_id_str = select_food(re_name)
        while True:
            if rec_id_str == 0:
                re_names.clear()
                id_num.clear()
                re_name = ingredient_search()
                rec_id_str = select_food(re_name)
            else:
                break
    elif way == "2":
        wanted = input("원하는 음식: ")
        re_name = name_search(wanted)
        rec_id_str = select_food(re_name)
        while True:
            if rec_id_str == 0:
                re_names.clear()
                id_num.clear()
                re_name = name_search(wanted)
                rec_id_str = select_food(re_name)
            else:
                break

    with open(path + '/' + rec_id_str + '.json', 'r', encoding='utf-8') as json_file:
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



recommend_recipe()
