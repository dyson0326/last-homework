# このプロジェクトについて

## 最終課題を作成します。

CRUD機能をもったREST APIを作成します。

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

### 名前の始まりを取得するAPI

- リクエスト
    - Method: GET
    - URL:/names?startsWith=｛｝
- レスポンス
    - ステータスコード: 200
    - ボディ: 名前のリストをJSON形式で返す

```curl
curl --location 'http://localhost:8080/names?startsWith=あ'
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

### 情報を登録するAPI

- リクエスト
    - Method: POST
    - URL:/names
- レスポンス
    - ステータスコード: 201
    - ボディ: "message": "pokemon created"を返す
    - 名前が重複した場合409を返す
    - 名前やType1が未入力の場合は400を返す
    - 指定したタイプ以外は400を返す

```curl
curl --location 'http://localhost:8080/names'
```

- リクエスト

```json
{
  "name": "全角カタカナ",
  "type1": "必須入力",
  "type2": ""
}
```

- 登録成功時のレスポンス

```
{
"message": "pokemon created"
}
```

- 名前が重複時のレスポンス

```
{
    "path": "/names",
    "error": "Conflict",
    "timestamp": "2024-10-11T05:20:03.710137900+09:00[Asia/Tokyo]",
    "message": "ポケモンは既に存在しています",
    "status": "409"
}
```

- 名前が未入力または不正なタイプが入力された時のレスポンス

```
{
  "status": "BAD_REQUEST",
  "message": "validation error",
  "errors": [
    {
      "field": "type1",
      "message": "無効なタイプです。"
    },
    {
      "field": "name",
      "message": "値を入力してください"
    }
  ]
}
```

### 情報を更新するAPI

- リクエスト
    - Method: PATCH
    - URL:/names/｛id｝
- レスポンス
    - ステータスコード: 200
    - ボディ: "message": "pokemon update"を返す
    - 存在しないIDをリクエストされた場合404を返す

```curl
curl --location 'http://localhost:8080/names/1'
```

- 登録成功時のレスポンス

```
{
"message": "pokemon created"
}
```

- 存在しないIDの場合のレスポンス

```json
{
  "message": "存在しないIDです",
  "timestamp": "2022-07-07T00:38:53.260151+09:00[Asia/Tokyo]",
  "error": "Not Found",
  "path": "/names/100",
  "status": "404"
}
```
