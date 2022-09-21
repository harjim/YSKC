using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class delTabelAndDelSuggestionCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_audit_log");

            migrationBuilder.DropColumn(
                name: "suggestion",
                table: "p_audit_report");

            migrationBuilder.DropColumn(
                name: "operationUser",
                table: "p_audit");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "suggestion",
                table: "p_audit_report",
                maxLength: 2000,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "operationUser",
                table: "p_audit",
                maxLength: 50,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "p_audit_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    auditId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    nodeId = table.Column<int>(nullable: false),
                    pass = table.Column<int>(nullable: false),
                    suggestion = table.Column<string>(maxLength: 2000, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_audit_log", x => x.id);
                });
        }
    }
}
