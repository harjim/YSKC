using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createUser_audit_summaryAndDetailTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "user_audit_detail",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    workDate = table.Column<DateTime>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    done = table.Column<int>(nullable: false),
                    ongoing = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_user_audit_detail", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "user_audit_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    workDate = table.Column<DateTime>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    done = table.Column<int>(nullable: false),
                    ongoing = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_user_audit_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_user_audit_detail_userId_workDate",
                table: "user_audit_detail",
                columns: new[] { "userId", "workDate" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_user_audit_summary_userId_workDate",
                table: "user_audit_summary",
                columns: new[] { "userId", "workDate" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "user_audit_detail");

            migrationBuilder.DropTable(
                name: "user_audit_summary");
        }
    }
}
