using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class removepTech : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_tech");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "project_tech",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    budget = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    closeDate = table.Column<DateTime>(nullable: false),
                    completedDate = table.Column<DateTime>(nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    lastLogId = table.Column<int>(nullable: true),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    ptName = table.Column<string>(maxLength: 50, nullable: false),
                    receivedDate = table.Column<DateTime>(nullable: false),
                    workContent = table.Column<string>(maxLength: 200, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_tech", x => x.id);
                });
        }
    }
}
