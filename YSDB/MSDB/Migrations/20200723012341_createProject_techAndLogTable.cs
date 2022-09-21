using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createProject_techAndLogTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "project_tech",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    ptName = table.Column<string>(maxLength: 50, nullable: false),
                    workContent = table.Column<string>(maxLength: 200, nullable: false),
                    budget = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    closeDate = table.Column<DateTime>(nullable: false),
                    receivedDate = table.Column<DateTime>(nullable: false),
                    completedDate = table.Column<DateTime>(nullable: false),
                    lastLogId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_tech", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "project_tech_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    techId = table.Column<int>(nullable: false),
                    process = table.Column<string>(maxLength: 200, nullable: false),
                    feedback = table.Column<string>(maxLength: 200, nullable: false),
                    stage = table.Column<string>(maxLength: 20, nullable: false),
                    status = table.Column<string>(maxLength: 20, nullable: false),
                    label = table.Column<string>(maxLength: 20, nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_tech_log", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_tech");

            migrationBuilder.DropTable(
                name: "project_tech_log");
        }
    }
}
