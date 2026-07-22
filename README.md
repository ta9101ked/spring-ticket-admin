# 簡易チケット管理（ticket-admin-sample）

社内向けの簡易チケット管理です。  
**一覧・検索・登録・更新・削除** を Spring Boot で実装した、業務画面改修の学習用ポートフォリオです。

> **実案件コードの公開ではありません。**  
> 同種ドメインの学習用再実装です。実装は自分で行い、設計判断も説明できる状態を目標にしています。

## できること

- チケットの CRUD（作成・一覧・編集・削除）
- タイトルキーワード検索 + ステータス絞り込み（AND 可）
- status / priority の選択式入力（表記揺れ防止）
- Bean Validation（必須・文字数・許可値）
- ticketNo 重複チェック（業務ルール）
- 存在しない ID の Not Found 画面

## 技術スタック

- Java 17
- Spring Boot 4.x
- Spring Web / Thymeleaf / Spring Data JPA / Validation
- H2（ローカル検証用・インメモリ）
- Maven Wrapper（`./mvnw`）

## 設計の要点

| 層 | 役割 |
|---|---|
| Controller | HTTP 受付、`@Valid`、画面遷移 |
| Service | 検索分岐、重複チェック、存在確認 |
| Repository | DB アクセス（Spring Data 命名クエリ） |
| Entity | データの形 + 入力ルール宣言 |

## 動かし方

```bash
# Java 17 を使う場合の例
source /Volumes/SSD-PHPU3A/java-gold-practice/env.sh

cd /path/to/ticket-admin-sample
./mvnw spring-boot:run
```

ブラウザ: [http://localhost:8080/tickets](http://localhost:8080/tickets)

## テスト

```bash
./mvnw test
```

- `TicketRepositoryTest` … 検索・存在確認
- `TicketServiceTest` … 重複・NotFound・更新
- `TicketControllerTest` … 一覧・作成・NotFound の画面遷移

## スクリーンショット

`docs/screenshots/` を参照。

## ドキュメント（学習用ウォーターフォール一式）

| 文書 | パス |
|---|---|
| 要件定義書 | [docs/01_requirements.md](docs/01_requirements.md) |
| 基本設計書 | [docs/02_basic_design.md](docs/02_basic_design.md) |
| 詳細設計書 | [docs/03_detail_design.md](docs/03_detail_design.md) |
| テスト仕様書 | [docs/04_test_spec.md](docs/04_test_spec.md) |
| 営業・提案キット | [docs/outreach/README.md](docs/outreach/README.md) |

> これらは **仮想クライアント（Web知見の少ない企業）を想定した学習用ドキュメント** です。  
> 実案件の成果物ではありません。実装と齟齬がある場合は文書側に明記します。

## 経歴との接続（正確に）

過去にクーポン／帳票系の API・DB 整理に **設計面で関わった** 経験があり、  
ステータスや検索条件の切り方にその知見を活かしています。  
本リポジトリのコードは新規実装です。

## 未対応・今後

- 認証・権限
- ページネーション
- CSV 出力
- 監査ログ
- 本番向け DB（PostgreSQL 等）

## ライセンス

学習・ポートフォリオ用途。
