using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class npatentDemand : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "demandId",
                table: "patent_plan",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "nodeExpired",
                table: "flowNode",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "nodeNumber",
                table: "flowNode",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "patent_demand",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    demandNo = table.Column<string>(maxLength: 10, nullable: true),
                    customerId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    type = table.Column<int>(nullable: false),
                    inventionNum = table.Column<int>(nullable: true),
                    utilityNum = table.Column<int>(nullable: true),
                    appearanceDesignNum = table.Column<int>(nullable: true),
                    total = table.Column<int>(nullable: false),
                    ownerId = table.Column<int>(nullable: true),
                    techId = table.Column<int>(nullable: false),
                    remark = table.Column<string>(maxLength: 200, nullable: true),
                    proxyPath = table.Column<string>(maxLength: 500, nullable: true),
                    blPath = table.Column<string>(maxLength: 500, nullable: true),
                    remissionPath = table.Column<string>(maxLength: 500, nullable: true),
                    otherPath = table.Column<string>(maxLength: 500, nullable: true),
                    planSubmitDate = table.Column<DateTime>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_demand", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "patent_demand");

            migrationBuilder.DropColumn(
                name: "demandId",
                table: "patent_plan");

            migrationBuilder.DropColumn(
                name: "nodeExpired",
                table: "flowNode");

            migrationBuilder.DropColumn(
                name: "nodeNumber",
                table: "flowNode");
        }
    }
}
