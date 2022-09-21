using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterT_beianColBeianNumToBeianNo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "beianNum",
                table: "t_beian",
                newName: "beianNo");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "beianNo",
                table: "t_beian",
                newName: "beianNum");
        }
    }
}
