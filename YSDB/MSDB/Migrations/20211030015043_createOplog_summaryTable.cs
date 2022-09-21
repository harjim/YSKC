using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createOplog_summaryTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "rsLastOptime",
                table: "ys_user",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "oplog_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    opdate = table.Column<DateTime>(nullable: false),
                    lastOptime = table.Column<DateTime>(nullable: false),
                    opCnt = table.Column<int>(nullable: false)
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

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "oplog_summary");

            migrationBuilder.DropColumn(
                name: "rsLastOptime",
                table: "ys_user");
        }
    }
}
