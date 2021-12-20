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

#식재료 기반 서치
def ingredient_search():
    recipes_info1 = []    #식재료 기반으로 찾은 레시피 목록
    necessary_ingre = []  #추가로 필요한 재료 목록

    #ingre_list = ['양파','당근']  #선택한 식재료 리스트
    ingre_list = sys.argv

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
            necessary_ingre = list(necessary_ingre)
            recipe_info1.append(len(necessary_ingre)) #추가로 필요한 재료 수
            recipe_info1.append(recipe_name[0])       #요리 이름
            recipe_info1.append(len(recipe_order_count1))     #레시피 단계
            recipe_info1.append(saved_ingre)          #나에게 저장된 재료
            recipe_info1.append(necessary_ingre)      #추가로 필요한 재료
            recipes_info1.append(recipe_info1)

    for recipe_infor1 in recipes_info1: #기본 요리이름 ㄱㄴㄷ 순
        if len(recipe_infor1[4]) <= 5: #추가로 필요한 식재료가 5개 이하인 레시피만 출력
            print(recipe_infor1)
            # print("추가로 필요한 재료 수 : " + str(recipe_infor1[0]))   #추가로 필요한 재료 수
            # print("요리 이름 : " + recipe_infor1[1])   #요리 이름
            # print("레시피 단계 : " + str(recipe_infor1[2]))   #레시피 단계
            # print("나에게 저장된 재료 : " + str(recipe_infor1[3]))   #나에게 저장된 재료
            # print("추가로 필요한 재료 : " + str(recipe_infor1[4]))   #추가로 필요한 재료
            # print(" ")

    # recipes_info1.sort()  
    # for recipe_infor1 in recipes_info1:   #추가로 필요한 재료가 적은 순
    #     print(recipe_infor1[0])   #추가로 필요한 재료 수
    #     print(recipe_infor1[1])   #요리 이름
    #     print(recipe_infor1[2])   #레시피 단계
    #     print(recipe_infor1[3])   #나에게 저장된 재료
    #     print(recipe_infor1[4])   #추가로 필요한 재료
    #     print(" ")

ingredient_search()