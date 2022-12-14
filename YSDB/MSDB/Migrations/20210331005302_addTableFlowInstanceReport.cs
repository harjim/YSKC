using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addTableFlowInstanceReport : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "flowInstance_report",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    rsProjectId = table.Column<int>(nullable: false),
                    instanceId = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_report", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowInstance_report");
        }
    }
}
