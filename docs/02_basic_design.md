# 基本設計書（学習用）

- 文書バージョン: 1.0
- 対応要件: `docs/01_requirements.md`

## 1. システム構成

```text
Browser (Thymeleaf HTML)
    ↓ HTTP
TicketController
    ↓
TicketService
    ↓
TicketRepository (Spring Data JPA)
    ↓
H2 (in-memory)
```

## 2. 画面一覧

| 画面ID | パス | 概要 |
|---|---|---|
| SCR-01 | `GET /tickets` | 一覧・検索 |
| SCR-02 | `GET /tickets/new` | 新規フォーム |
| SCR-03 | `POST /tickets` | 新規登録 |
| SCR-04 | `GET /tickets/{id}/edit` | 編集フォーム |
| SCR-05 | `POST /tickets/{id}/edit` | 更新 |
| SCR-06 | `POST /tickets/{id}/delete` | 削除 |
| SCR-07 | （例外時）`error/not-found` | 未存在 |

## 3. 画面遷移

```text
一覧 --New--> 新規 --Create成功--> 一覧
一覧 --Edit--> 編集 --Update成功--> 一覧
一覧 --Delete--> 確認OK --> 一覧
編集（不正ID）--> Not Found --> 一覧
```

## 4. 論理データモデル

### Ticket

| 論理名 | 物理名 | 型 | 制約 |
|---|---|---|---|
| ID | id | Long | PK, 自動採番 |
| チケット番号 | ticket_no | String(40) | UNIQUE, NOT NULL |
| 件名 | title | String(120) | NOT NULL |
| 状態 | status | String(20) | NOT NULL |
| 優先度 | priority | String(20) | NOT NULL |
| 依頼者名 | requester_name | String(80) | NOT NULL |

## 5. 処理概要

| 処理 | 概要 |
|---|---|
| 検索 | keyword 空/status 空の組み合わせで Repository メソッドを切替 |
| 登録 | Validation → 重複チェック → insert |
| 更新 | 存在確認 → Validation → 重複チェック（自ID除外）→ update |
| 削除 | 存在確認 → delete |

## 6. エラー方針

| 種別 | 扱い |
|---|---|
| 入力エラー | 同一フォーム再表示 + 項目下にメッセージ |
| 番号重複 | 同一フォーム再表示 + ticketNo エラー |
| ID未存在 | Not Found 画面 |

## 7. 実装との対応

実装リポジトリ: `ticket-admin-sample`  
主なクラス: `TicketController`, `TicketService`, `TicketRepository`, `Ticket`
