using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class delproject_progressTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_progress");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "project_progress",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    operationTime = table.Column<DateTime>(nullable: false),
                    stage = table.Column<int>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_progress", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_project_progress_companyId_stage_year",
                table: "project_progress",
                columns: new[] { "companyId", "stage", "year" },
                unique: true);
        }
    }
}
