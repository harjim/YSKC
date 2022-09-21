using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class i_member_role : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "role",
                table: "i_member",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "effect",
                table: "i_equipment",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "specialities",
                table: "employee",
                maxLength: 100,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "role",
                table: "i_member");

            migrationBuilder.DropColumn(
                name: "effect",
                table: "i_equipment");

            migrationBuilder.DropColumn(
                name: "specialities",
                table: "employee");
        }
    }
}
