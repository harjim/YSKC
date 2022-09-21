using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class createUser_expert_logTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "status",
                table: "user_expert",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "user_expert_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    userExpertId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    suggestion = table.Column<string>(maxLength: 2000, nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_user_expert_log", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "user_expert_log");

            migrationBuilder.DropColumn(
                name: "status",
                table: "user_expert");
        }
    }
}
