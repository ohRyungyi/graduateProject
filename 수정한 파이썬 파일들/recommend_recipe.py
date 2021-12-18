# -*- coding: utf-8 -*-
import pymysql.cursors
import json
import os
import sys
#from yolo import labels_kor

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

#음식 선택
def select_food():
    #name = "카레라이스"
    name = sys.argv
    sql7 = "select id from RecipeData where recipe_name = %s;"
    curs.execute(sql7,name)
    db_id = list(curs.fetchall())
    conn.commit()

    r_db_id = db_id[0]
    r_db_id_int = int(r_db_id[0])
    id_num_int = r_db_id_int

    sql8 = "select recipe_id from RecipeData where id = %s;"
    curs.execute(sql8, id_num_int)
    r_id = list(curs.fetchall())
    conn.commit()

    rr_id = r_id[0]
    rr_id_int = int(rr_id[0])
    rr_id_str = str(rr_id_int)

    return rr_id_str

#레시피 제공
def recommend_recipe():

    rec_id_str = select_food()

    with open(path + '/' + rec_id_str + '.json', 'r', encoding='utf-8') as json_file:
        json_data = json.load(json_file)
        recipe_order = json_data["recipe"]
        print(recipe_order)

recommend_recipe()