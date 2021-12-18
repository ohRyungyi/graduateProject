# -*- coding: utf-8 -*-
import pymysql.cursors
import json
import os
import sys

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

#이름기반 서치
def name_search():
    recipes_info2 = []
    sql4 = "select recipe_name,id from RecipeData order by recipe_name;"
    curs.execute(sql4)
    recipes_name_id = list(curs.fetchall())
    conn.commit()

    #wanted = '돼지고기'
    wanted = sys.argv

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
            with open(path + '/' + rrrr_id_str + '.json', 'r', encoding='utf-8') as json_file:
                json_data = json.load(json_file)
                recipe_order_count2 = json_data["recipe"]
            recipe_info2.append(recipe_name0)             #요리 이름
            recipe_info2.append(len(recipe_order_count2)) #레시피 단계
            recipe_info2.append(r_ingre4)                 #레시피 재료
            recipes_info2.append(recipe_info2)

            re_names.append(recipe_name0)
            id_num.append(recipe_id0)

    for recipe_infor2 in recipes_info2:    #기본 요리 이름 ㄱㄴㄷ 순서
        print(recipe_infor2)
        # print(recipe_infor2[0])   #요리 이름
        # print(recipe_infor2[1])   #레시피 단계
        # print(recipe_infor2[2])   #레시피 재료
        # print("  ")
    #for a in re_names:
    #    print(a)

name_search()