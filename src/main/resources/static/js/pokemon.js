function addPokemon(event) {
    event.preventDefault();

    var formData = new FormData(document.getElementById('pokemonForm'));
    var xhr = new XMLHttpRequest();

    xhr.open('POST', '/pokemon/newnames', true);

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            var response = JSON.parse(xhr.responseText);
            document.getElementById('messageContainer').textContent = response.message;
            loadPokemonList();
        } else {
            document.getElementById('messageContainer').textContent = 'エラーが発生しました。';
        }
    };

    xhr.send(formData);
}

function loadPokemonList() {

    $.ajax({
        url: '/pokemon/listnames',  // ポケモン一覧を取得するURL
        type: 'GET',  // GETメソッド
        dataType: 'json',  // JSON形式でデータを受け取る
        success: function(data) {
            console.log("ポケモンリスト取得成功:", data);

            var pokemonTableBody = $('#pokemonTableBody');
            pokemonTableBody.empty();  // 既存のテーブル行をクリア

            data.forEach(function(pokemon) {
                var row = $('<tr>');  // 新しい行を作成

                row.append('<td>' + pokemon.id + '</td>');
                row.append('<td><input type="text" id="name-' + pokemon.id + '" class="pokemon-name" value="' + pokemon.name + '" data-id="' + pokemon.id + '"></td>');
                row.append('<td><input type="text" id="type1-' + pokemon.id + '" class="pokemon-type1" value="' + pokemon.type1 + '" data-id="' + pokemon.id + '"></td>');
                row.append('<td><input type="text" id="type2-' + pokemon.id + '" class="pokemon-type2" value="' + pokemon.type2 + '" data-id="' + pokemon.id + '"></td>');
                row.append('<td><button type="button" class="update-button" data-id="' + pokemon.id + '">更新</button></td>');
                row.append('<td><button type="button" class="delete-button" data-id="' + pokemon.id + '">削除</button></td>');

                pokemonTableBody.append(row);
            });

                $('.update-button').on('click', function(event) {
                    var pokemonId = $(this).data('id');  // ボタンのdata-id属性からポケモンIDを取得
                    updatePokemon(event, pokemonId);  // updatePokemon関数を呼び出し
                        });

                $('.delete-button').on('click', function(event) {
                    var pokemonId = $(this).data('id');  // ボタンのdata-id属性からポケモンIDを取得
                        deletePokemon(event, pokemonId);  // deletePokemon関数を呼び出し
                        });
                    },

        error: function(xhr, status, error) {
            console.error("AJAXエラー:", status, error);
            alert('ポケモン一覧の取得に失敗しました。');
        }
    });
}

function updatePokemon(event, pokemonId) {
    event.preventDefault();

    var name = document.getElementById('name-' + pokemonId).value;
    var type1 = document.getElementById('type1-' + pokemonId).value;
    var type2 = document.getElementById('type2-' + pokemonId).value;

    var data = 'name=' + encodeURIComponent(name) +
                   '&type1=' + encodeURIComponent(type1) +
                   '&type2=' + encodeURIComponent(type2);

    var xhr = new XMLHttpRequest();
    xhr.open('PATCH', '/pokemon/updatenames/' + pokemonId, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            var response = JSON.parse(xhr.responseText);  // JSONレスポンスを解析
            document.getElementById('messageContainer').textContent = response.message;
            loadPokemonList();
        } else {
            document.getElementById('messageContainer').textContent = 'エラーが発生しました。';
        }
    };

    xhr.send(data);
}

function deletePokemon(event, pokemonId) {
    event.preventDefault();

    var xhr = new XMLHttpRequest();

    xhr.open('DELETE', '/pokemon/deletenames/' + pokemonId, true);

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            var response = JSON.parse(xhr.responseText);  // JSONレスポンスを解析
            document.getElementById('messageContainer').textContent = response.message;
            loadPokemonList();
        } else {
            document.getElementById('messageContainer').textContent = 'エラーが発生しました。';
        }
    };

    xhr.send('_method=DELETE');
}
