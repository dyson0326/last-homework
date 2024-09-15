# このプロジェクトについて

## 最終課題を作成します。

今回はCRUD機能をもったREST APIを作成します。

## 開発環境

-Java 19
-Spring Boot 3.3.3
-MySQL 8

## APIの概要

### 名前を全件取得するAPI

- リクエスト
    - Method: GET
    - URL:/names
- レスポンス
    - ステータスコード: 200
    - ボディ: 名前のリストをJSON形式で返す

```curl
curl --location 'http://localhost:8080/names'
```

### 指定したIDの名前を取得するAPI

- リクエスト
    - Method: GET
    - URL:/names/｛id｝
- レスポンス
    - ステータスコード: 200
    - ボディ: 名前のリストをJSON形式で返す
    - IDが存在しない場合はステータスコード404を返す

```curl
curl --location 'http://localhost:8080/names/1'
```

- 200の場合のレスポンス

```json
{
  "id": 1,
  "name": "",
  "type1": "",
  "type2": ""
}
```

- 404の場合のレスポンス

```json
{
  "message": "pokemon not found",
  "timestamp": "2022-07-07T00:38:53.260151+09:00[Asia/Tokyo]",
  "error": "Not Found",
  "path": "/names/100",
  "status": "404"
}
```