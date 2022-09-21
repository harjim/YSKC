using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addRdEmployeePlanTab : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_rdEmployee_plan",
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
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    enumber = table.Column<string>(maxLength: 30, nullable: false),
                    planTime = table.Column<decimal>(type: "decimal(18,2)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_rdEmployee_plan", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_rdEmployee_plan");
        }
    }
}
