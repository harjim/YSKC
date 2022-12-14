using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createproject_summaryTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "project_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    projectId = table.Column<int>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    cnt = table.Column<int>(nullable: true),
                    rdCount = table.Column<int>(nullable: true),
                    budget = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    rdFunds = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    docFileCount = table.Column<int>(nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    updateTime = table.Column<DateTime>(nullable: false),
                    operationTime = table.Column<DateTime>(nullable: true),
                    operationUserId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_summary", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_summary");
        }
    }
}
