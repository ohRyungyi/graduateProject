var express = require('express');
const childProcess = require('child_process');
const { stat } = require('fs');

var router = express.Router();

router.post('/searchRecipeByName' , function(req ,res ,next) {
    console.log('/recipes/searchRecipeByName start --------------------------------------');
    console.log('body : '+JSON.stringify(req.body)+' , '+req.body.name);

    var status = 0;
    var result = new Object();

    console.log('body : '+JSON.stringify(req.body)+' , '+req.body.name);
    let spawn = childProcess.spawn('python3', ['../name_search.py' , req.body.name]);
    spawn.stdout.on('data' , function(data) {
        console.log('output : '+data +" , type : "+typeof(data));

        console.log('vallue : '+data.toString())
        var str = data.toString();
        var st = str.split('\n')
        console.log('size : '+st.length+ " , arr : "+st[0])

        var datas = new Array();

        for ( var i =0 ; i<st.length-1 ; i++ ) {
            var strs = st[i];
            strs = strs.substring(1, strs.length-1)
            console.log(i+") total : "+strs)
            var ingredients = strs.split('[')[1]
            ingredients = ingredients.substring(0, ingredients.length-1)
            ingredients = ingredients.replace( /\'/g, '')
            ingredients = ingredients.replace(/ /g, '')
            console.log("ingredients : "+ingredients)
            var namep = strs.split('[')[0]
            var nameList = namep.split('\'')
            // for ( i in nameList ) {
            //     console.log('i : '+i+" , other : "+nameList[i])
            // }
            // console.log('others : '+nameList)
            var stepList = namep.split(',')
            // console.log('len : '+" , "+stepList.length)
            // for ( i in stepList ) {
            //     console.log('step : '+stepList[i])
            // }

            var one = new Object();
            one.name = nameList[1]
            var step =  stepList[ stepList.length-2].replace(/\'/g , '')
            step = step.replace(/ /g , '')
            one.step = step
            one.ingredients = ingredients
            // one.push("name" , nameList[1])
            // one.push("step" , nameList[3])
            // one.push("ingredients" , ingredients)
            // one.push(nameList[1])
            // one.push(nameList[3])
            // one.push(ingredients)
            datas.push(one)
        }

        result.success = true;
        result.msg  = 'success to run name_search'
        result.data = datas

        status = 200
    })
    spawn.stderr.on('data' , function(data) {
        console.log('error : '+data);

        let datas = new Array();
        

        result.success = false;
        result.msg  = 'fail to run name_search'
        result.data = datas

        status = 401
    });
    spawn.on('exit' , function(code , signal) {
        console.log('exit');

        console.log(result)
        return res.status(status).json(result)
    });
});

router.post('/searchRecipeByIngredients' , function(req, res, next) {
    console.log('/recipes/searchRecipeByIngredients start --------------------------------------');
    console.log('body : '+JSON.stringify(req.body)+" , "+req.body.ingredients);

    var status = 0;
    var result = new Object();

    var st = req.body.ingredients.split(',')

    var param = []
    param.push("../ingredient_search.py")

    for ( var i=0 ; i<st.length ; i++ ) {
        var ingredient = st[i]
        param.push( ingredient)
    }

    // console.log(param)
    let spawn = childProcess.spawn('python3' , param)
    spawn.stdout.on('data' , function(data) {
        var data_str = data.toString();
        var item_str = data_str.split('\n');

        var data_list = new Array();

        for ( var i=0 ; i<item_str.length-1 ; i++ ) {
            console.log(item_str[i])

            var item = item_str[i].substring(1, item_str[i].length-1)
            console.log(item)

            var non_selected = item.split('[')[2]
            non_selected = non_selected.split(']')[0]
            non_selected = non_selected.replace( /\'/g, '')
            non_selected = non_selected.replace(/ /g, '')
            console.log('미선택 식재료 리스트 : '+non_selected)

            var selected = item.split('[')[1]
            selected = selected.split(']')[0]
            selected = selected.replace( /\'/g, '')
            selected = selected.replace(/ /g, '')
            console.log('선택되 식재료 리스트 : '+selected)

            var ingredients = selected+","+non_selected

            var name = item.split(',')[1]
            name = name.replace(/\'/g, '')
            console.log('name : '+name)

            var step = item.split(',')[2]
            step = step.replace('\'', '')
            console.log('step : '+step)

            var data = new Object();
            data.name = name.trim();
            data.step = step.trim();
            data.ingredient = ingredients.trim();

            data_list.push(data)    
        }

        result.success = true;
        result.msg = "success to run ingredient_search"
        result.data = data_list;

        status = 200;
    });
    spawn.stderr.on('data' , function(data) {
        result.success = false;
        result.msg = "fail to run ingredient_search"
        result.data = new Array();

        status = 401;

    });
    spawn.on('exit' , function(code, signal) {
        return res.status(status).json(result)
    });

});
router.post('/getRecipe' , function(req, res, status){
    console.log('/recipes/getRecipe start ----------------------------');
    console.log('body : '+req.body.name);

    var status = 0;
    var result = new Object();

    let spawn = childProcess.spawn('python3' , ['../recommend_recipe.py' , req.body.name.toString() ])
    spawn.stdout.on('data' , function(data) {
        var data_str = data.toString()
        data_str = data_str.substring(2, data_str.length-3)

        console.log(data_str)

        var data_list = data_str.split('\', \'');
        var res_data = new Array()
        // print
        for ( i in data_list ) {
            var temp = data_list[i].split('. ')[1]
            console.log(temp)
            res_data.push(temp)
            // res_data+=temp+","
        }



        result.success = true;
        result.msg = "success to run recommend_recipe"
        result.data = res_data;

        status = 200;
    });
    spawn.stderr.on('data' , function(data) {
        result.success = false;
        result.msg = "fail to run recommend_recipe"
        result.data = new Array();

        status = 401;
    });
    spawn.on('exit' , function(code, signal) {
        console.log('respone : '+result.data)
        return res.status(status).json(result);
    })
});
module.exports = router;