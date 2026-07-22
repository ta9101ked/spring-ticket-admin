# 詳細設計書（学習用）

- 文書バージョン: 1.0

## 1. クラス設計

### TicketController
- 依存: `TicketService`
- `@ModelAttribute addFormOptions`: 毎回 `statuses` / `priorities` を Model へ
- 主要メソッド: `getAllTickets`, `newForm`, `create`, `edit`, `update`, `delete`

### TicketService
- `search(keyword, status)`
- `findById(id)` → 無ければ `TicketNotFoundException`
- `create(ticket)` → 重複なら `DuplicateTicketNoException`
- `update(id, ticket)` → 存在確認 + 重複確認 + `setId(id)`
- `delete(id)` → 存在確認後削除

### TicketRepository（派生クエリ）
- `findByTitleContaining`
- `findByStatus`
- `findByTitleContainingAndStatus`
- `existsByTicketNo`
- `existsByTicketNoAndIdNot`

### Ticket（バリデーション）
- `@NotBlank`, `@Size`, `@Pattern` をフィールドに宣言

## 2. API / 画面パラメータ

### GET /tickets
| パラメータ | 必須 | 説明 |
|---|---|---|
| keyword | 任意 | タイトル部分一致 |
| status | 任意 | 完全一致（Open / In_Progress / Done） |

### POST /tickets / POST /tickets/{id}/edit
| 項目 | 制約 |
|---|---|
| ticketNo | 必須, max 40, unique |
| title | 必須, max 120 |
| status | Open\|In_Progress\|Done |
| priority | Low\|Medium\|High |
| requesterName | 必須, max 80 |

## 3. 例外

| 例外 | HTTP相当の扱い（本アプリ） |
|---|---|
| `TicketNotFoundException` | `error/not-found` を表示 |
| `DuplicateTicketNoException` | フォームへフィールドエラー |

## 4. 定数

- `TicketStatus.ALL`
- `TicketPriority.ALL`
