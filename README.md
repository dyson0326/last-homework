# このプロジェクトについて

## 最終課題を作成します。

今回はCRUD機能をもったREST APIを作成します。

## 開発環境

-Java 19
-Spring Boot 3.3.3
-MySQL 8

## APIの概要

### 名前を全件取得する　API

- リクエスト
    - Method: GET
    - URL:/names
- レスポンス
    - ステータスコード: 200
    - ボディ: 名前のリストをJSON形式で返す

'''
curl --location 'http://localhost:8080/names/1000'
'''