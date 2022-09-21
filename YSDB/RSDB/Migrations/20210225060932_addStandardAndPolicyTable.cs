using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addStandardAndPolicyTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_policy_summary",
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
                    statute = table.Column<string>(maxLength: 1000, nullable: true),
                    filingMaterial = table.Column<string>(maxLength: 500, nullable: true),
                    additionalDeduction = table.Column<string>(maxLength: 500, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_policy_summary", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_standard_implement",
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
                    year = table.Column<int>(nullable: false),
                    rdCenterBuild = table.Column<string>(maxLength: 500, nullable: true),
                    rdProjectManage = table.Column<string>(maxLength: 500, nullable: true),
                    rdBudget = table.Column<string>(maxLength: 500, nullable: true),
                    intellectualManage = table.Column<string>(maxLength: 500, nullable: true),
                    highTech = table.Column<string>(maxLength: 500, nullable: true),
                    fileManage = table.Column<string>(maxLength: 500, nullable: true),
                    thoughtManage = table.Column<string>(maxLength: 500, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_standard_implement", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_policy_summary");

            migrationBuilder.DropTable(
                name: "p_standard_implement");
        }
    }
}
