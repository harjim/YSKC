using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterUser_auditTableNamesToDaily_report : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "user_audit_detail");

            migrationBuilder.DropTable(
                name: "user_audit_summary");

            migrationBuilder.CreateTable(
                name: "daily_report",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    workDate = table.Column<DateTime>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    done = table.Column<int>(nullable: false),
                    commit = table.Column<int>(nullable: false),
                    reject = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_daily_report", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "daily_report_detail",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userId = table.Column<int>(nullable: false),
                    workDate = table.Column<DateTime>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    done = table.Column<int>(nullable: false),
                    commit = table.Column<int>(nullable: false),
                    reject = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_daily_report_detail", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_daily_report_userId_workDate_companyId",
                table: "daily_report",
                columns: new[] { "userId", "workDate", "companyId" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_daily_report_detail_userId_workDate_moduleId_companyId",
                table: "daily_report_detail",
                columns: new[] { "userId", "workDate", "moduleId", "companyId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "daily_report");

            migrationBuilder.DropTable(
                name: "daily_report_detail");

            migrationBuilder.CreateTable(
                name: "user_audit_detail",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    commit = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    done = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false),
                    reject = table.Column<int>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    workDate = table.Column<DateTime>(nullable: false)
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
                    commit = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    done = table.Column<int>(nullable: false),
                    reject = table.Column<int>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    workDate = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_user_audit_summary", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_user_audit_detail_userId_workDate_moduleId",
                table: "user_audit_detail",
                columns: new[] { "userId", "workDate", "moduleId" },
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_user_audit_summary_userId_workDate",
                table: "user_audit_summary",
                columns: new[] { "userId", "workDate" },
                unique: true);
        }
    }
}
