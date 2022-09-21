using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterOplogToOprationLog : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "oplog_summary");

            migrationBuilder.RenameColumn(
                name: "rsLastOptime",
                table: "ys_user",
                newName: "rsLastOperationTime");

            migrationBuilder.CreateTable(
                name: "operation_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    operationDate = table.Column<DateTime>(nullable: false),
                    lastOperationtime = table.Column<DateTime>(nullable: false),
                    operationCnt = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_operation_log", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_operation_log_userId_operationDate",
                table: "operation_log",
                columns: new[] { "userId", "operationDate" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "operation_log");

            migrationBuilder.RenameColumn(
                name: "rsLastOperationTime",
                table: "ys_user",
                newName: "rsLastOptime");

            migrationBuilder.CreateTable(
                name: "oplog_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    lastOptime = table.Column<DateTime>(nullable: false),
                    opCnt = table.Column<int>(nullable: false),
                    opdate = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_oplog_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_oplog_summary_userId_opdate",
                table: "oplog_summary",
                columns: new[] { "userId", "opdate" },
                unique: true);
        }
    }
}
