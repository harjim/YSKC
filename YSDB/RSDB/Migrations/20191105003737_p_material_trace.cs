using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class p_material_trace : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_material_trace",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    pId = table.Column<int>(nullable: false),
                    normalOutputRate = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    rdOutputRate = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    rdOutPut = table.Column<int>(nullable: false),
                    rdOutputAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    rdLossRate = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    rdLoss = table.Column<int>(nullable: false),
                    rdLossAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    scrapRate = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    scrap = table.Column<int>(nullable: false),
                    scrapAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    scrapNo = table.Column<string>(maxLength: 100, nullable: true),
                    sampleRevenue = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    scrapPrice = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    specialIncome = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    specialIncomeNo = table.Column<string>(maxLength: 100, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_material_trace", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_material_trace");
        }
    }
}
