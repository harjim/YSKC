using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class moveTech_reqirementTableToRS : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "tech_requirement",
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
                    linkName = table.Column<string>(maxLength: 20, nullable: false),
                    position = table.Column<string>(maxLength: 50, nullable: false),
                    linkTel = table.Column<string>(maxLength: 50, nullable: false),
                    linkEmail = table.Column<string>(maxLength: 50, nullable: false),
                    description = table.Column<string>(maxLength: 300, nullable: false),
                    filePath = table.Column<string>(maxLength: 500, nullable: false),
                    techName = table.Column<string>(maxLength: 300, nullable: false),
                    domain = table.Column<string>(maxLength: 50, nullable: false),
                    investment = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    requirement = table.Column<string>(maxLength: 300, nullable: false),
                    cooperateType = table.Column<string>(maxLength: 50, nullable: false),
                    year = table.Column<int>(nullable: false),
                    otherDomain = table.Column<string>(maxLength: 100, nullable: true),
                    otherCooperateType = table.Column<string>(maxLength: 100, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tech_requirement", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "tech_requirement");
        }
    }
}
