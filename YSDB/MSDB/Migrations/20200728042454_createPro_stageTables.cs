using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createPro_stageTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "label",
                table: "project_tech_log");

            migrationBuilder.DropColumn(
                name: "stage",
                table: "project_tech_log");

            migrationBuilder.AlterColumn<int>(
                name: "status",
                table: "project_tech_log",
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 20);

            migrationBuilder.AddColumn<decimal>(
                name: "budget",
                table: "project_tech_log",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<DateTime>(
                name: "completedDate",
                table: "project_tech_log",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "labelKey",
                table: "project_tech_log",
                maxLength: 10,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "stageId",
                table: "project_tech_log",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "formalName",
                table: "product",
                maxLength: 50,
                nullable: false,
                defaultValue: "");

            migrationBuilder.CreateTable(
                name: "product_stage",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    order = table.Column<int>(nullable: false),
                    stageKey = table.Column<string>(maxLength: 10, nullable: false),
                    productId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_product_stage", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "project_tech_stage",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    stageId = table.Column<int>(nullable: false),
                    deadline = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_tech_stage", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "product_stage");

            migrationBuilder.DropTable(
                name: "project_tech_stage");

            migrationBuilder.DropColumn(
                name: "budget",
                table: "project_tech_log");

            migrationBuilder.DropColumn(
                name: "completedDate",
                table: "project_tech_log");

            migrationBuilder.DropColumn(
                name: "labelKey",
                table: "project_tech_log");

            migrationBuilder.DropColumn(
                name: "stageId",
                table: "project_tech_log");

            migrationBuilder.DropColumn(
                name: "formalName",
                table: "product");

            migrationBuilder.AlterColumn<string>(
                name: "status",
                table: "project_tech_log",
                maxLength: 20,
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "label",
                table: "project_tech_log",
                maxLength: 20,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "stage",
                table: "project_tech_log",
                maxLength: 20,
                nullable: false,
                defaultValue: "");
        }
    }
}
